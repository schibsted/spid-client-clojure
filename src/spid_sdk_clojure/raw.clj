(ns spid-sdk-clojure.raw)

(defn GET [client endpoint & [parameters]]
  (-> client (.GET endpoint (or parameters {}))))

(defn POST [client endpoint parameters]
  (-> client (.POST endpoint parameters)))

(defn PUT [client endpoint parameters]
  (-> client (.PUT endpoint parameters)))

(defn DELETE [client endpoint]
  (-> client (.DELETE endpoint)))
