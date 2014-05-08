(ns spid-sdk-clojure.core
  (:import [no.spid.api.client SpidApiClient$ClientBuilder SpidApiResponse]
           [no.spid.api.oauth SpidOAuthToken]
           [no.spid.api.exceptions SpidApiException])
  (:require [clojure.data.json :as json]
            [clojure.walk :refer [stringify-keys]]))

(defn- json-parse [data]
  (json/read-str data :key-fn keyword))

(defn- mapify-response [response]
  (let [json (json-parse (.getRawBody response))]
    {:body (.getRawBody response)
     :status (.getResponseCode response)
     :error (:error json)
     :data (:data json)
     :container json
     :success? (<= 200 (.getResponseCode response) 299)}))

(defn- mapify-error [error]
  (let [json (json-parse (.getResponseBody error))]
    {:body (.getResponseBody error)
     :status (.getResponseCode error)
     :error (:error json)
     :container json
     :success? false}))

(def defaults
  {:spid-base-url "https://stage.payment.schibsted.no"
   :redirect-uri "http://localhost:8080"})

(defn create-server-client [client-id secret & [options]]
  (let [options (merge defaults options)]
    (-> (SpidApiClient$ClientBuilder. client-id
                                      secret
                                      (:signature-secret options)
                                      (:redirect-uri options)
                                      (:spid-base-url options))
        (.build))))

(defn create-server-token [client]
  (.getServerToken client))

(defmacro request [forms]
  `(try
     (mapify-response (~@forms))
     (catch SpidApiException e#
       (mapify-error e#))))

(defn GET [client token endpoint & [parameters]]
  (request (.GET client token endpoint (stringify-keys (or parameters {})))))

(defn POST [client token endpoint & [parameters]]
  (request (.POST client token endpoint (stringify-keys parameters))))

(defn PUT [client token endpoint & [parameters]]
  (request (.PUT client token endpoint (stringify-keys parameters))))

(defn DELETE [client token endpoint & [parameters]]
  (request (.DELETE client token endpoint)))
