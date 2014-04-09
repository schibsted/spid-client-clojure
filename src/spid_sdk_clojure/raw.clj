(ns spid-sdk-clojure.raw
  (:require [clojure.data.json :as json])
  (:import no.spp.sdk.exception.SPPClientResponseException))

(defn- json-parse [data]
  (json/read-str data :key-fn keyword))

(defn- mapify-response [response]
  {:body (.getResponseBody response)
   :status (.getHttpResponseCode response)
   :error (.getError response)
   :data (and (.getData response) (json-parse (.getData response)))})

(defn- mapify-error [error]
  {:body (.getResponseBody error)
   :status (.getHttpResponseCode error)
   :error (:error (json-parse (.getResponseBody error)))})

(defmacro request [forms]
  `(try
     (mapify-response (~@forms))
     (catch SPPClientResponseException e#
       (mapify-error e#))))

(defn GET [client endpoint & [parameters]]
  (request (.GET client endpoint (or parameters {}))))

(defn POST [client endpoint parameters]
  (-> client (.POST endpoint parameters) mapify-response))

(defn PUT [client endpoint parameters]
  (-> client (.PUT endpoint parameters) mapify-response))

(defn DELETE [client endpoint]
  (-> client (.DELETE endpoint) mapify-response))
