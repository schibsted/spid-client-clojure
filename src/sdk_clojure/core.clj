(ns sdk-clojure.core
  (:import [no.spp.sdk.client ServerClientBuilder]
           [no.spp.sdk.oauth ClientCredentials])
  (:require [clojure.data.json :as json]
            [clojure.walk :refer [keywordize-keys]]))

(def defaults
  {:spp-base-url "https://stage.payment.schibsted.no"
   :redirect-uri "http://localhost:8080"})

(defn create-client [client-id secret & [options]]
  (let [options (merge options defaults)]
    (-> (ClientCredentials. client-id secret (:redirect-uri options))
        (ServerClientBuilder.)
        (.withBaseUrl (:spp-base-url options))
        (.build))))

(defn GET [client end-point]
  (-> client
      (.GET end-point)
      (.getResponseBody)
      (json/read-str :key-fn keyword)))
