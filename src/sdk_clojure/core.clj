(ns sdk-clojure.core
  (:import [no.spp.sdk.client ServerClientBuilder SPPClientResponse]
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

(defn- cleanup [^SPPClientResponse response]
  (-> response
      (.getResponseBody)
      (json/read-str :key-fn keyword)))

(defn GET [client end-point & [parameters]]
  (-> client (.GET end-point (or parameters {})) cleanup))

(defn POST [client end-point parameters]
  (-> client (.POST end-point parameters) cleanup))

(defn PUT [client end-point parameters]
  (-> client (.PUT end-point parameters) cleanup))

(defn DELETE [client end-point]
  (-> client (.DELETE end-point) cleanup))
