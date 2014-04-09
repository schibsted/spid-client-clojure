(ns spid-sdk-clojure.raw
  (:require [clojure.data.json :as json])
  (:import no.spp.sdk.exception.SPPClientResponseException))

(defn- json-parse [data]
  (json/read-str data :key-fn keyword))

(defn- mapify-response [response]
  {:body (.getResponseBody response)
   :status (.getHttpResponseCode response)
   ;; Can't use (.getError response), because it will return "null" (a string)
   ;; most of the time
   :error (:error (.getJsonContainer response))
   :data (and (.getData response) (json-parse (.getData response)))
   :success? (<= 200 (.getHttpResponseCode response) 299)})

(defn- mapify-error [error]
  {:body (.getResponseBody error)
   :status (.getHttpResponseCode error)
   :error (:error (json-parse (.getResponseBody error)))
   :success? false})

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
