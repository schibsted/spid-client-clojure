(ns spid-sdk-clojure.raw
  (:require [clojure.data.json :as json]))

(defn mapify-response [response]
  {:body (.getResponseBody response)
   :status (.getHttpResponseCode response)
   :error (.getError response)
   :data (json/read-str (.getData response) :key-fn keyword)})

(defn GET [client endpoint & [parameters]]
  (-> client (.GET endpoint (or parameters {})) mapify-response))

(defn POST [client endpoint parameters]
  (-> client (.POST endpoint parameters) mapify-response))

(defn PUT [client endpoint parameters]
  (-> client (.PUT endpoint parameters) mapify-response))

(defn DELETE [client endpoint]
  (-> client (.DELETE endpoint) mapify-response))
