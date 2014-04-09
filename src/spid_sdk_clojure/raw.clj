(ns spid-sdk-clojure.raw)

(defn mapify-response [response]
  {:body (.responseBody response)
   :status (.httpResponseCode response)
   :error (.error response)
   :data (.data response)})

(defn GET [client endpoint & [parameters]]
  (-> client (.GET endpoint (or parameters {})) mapify-response))

(defn POST [client endpoint parameters]
  (-> client (.POST endpoint parameters) mapify-response))

(defn PUT [client endpoint parameters]
  (-> client (.PUT endpoint parameters) mapify-response))

(defn DELETE [client endpoint]
  (-> client (.DELETE endpoint) mapify-response))
