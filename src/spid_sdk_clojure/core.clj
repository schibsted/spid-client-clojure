(ns spid-sdk-clojure.core
  (:import [no.spp.sdk.client ServerClientBuilder UserClientBuilder SPPClientResponse]
           [no.spp.sdk.oauth ClientCredentials])
  (:require [clojure.data.json :as json]))

(def defaults
  {:spp-base-url "https://stage.payment.schibsted.no"
   :redirect-uri "http://localhost:8080"})

(defn create-server-client [client-id secret & [options]]
  (let [options (merge options defaults)]
    (-> (ClientCredentials. client-id secret (:redirect-uri options))
        (ServerClientBuilder.)
        (.withBaseUrl (:spp-base-url options))
        (.build))))

(defn create-user-client [code client-id secret & [options]]
  (let [options (merge options defaults)]
    (-> (ClientCredentials. client-id secret (:redirect-uri options))
        (UserClientBuilder.)
        (.withUserAuthorizationCode code)
        (.withBaseUrl (:spp-base-url options))
        (.build))))

(defn- parse-response [^SPPClientResponse response]
  (-> response
      (.getResponseBody)
      (json/read-str :key-fn keyword)))

(defn GET [client end-point & [parameters]]
  (-> client (.GET end-point (or parameters {})) parse-response))

(defn POST [client end-point parameters]
  (-> client (.POST end-point parameters) parse-response))

(defn PUT [client end-point parameters]
  (-> client (.PUT end-point parameters) parse-response))

(defn DELETE [client end-point]
  (-> client (.DELETE end-point) parse-response))
