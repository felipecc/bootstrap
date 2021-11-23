(ns bootstrap.components.db
  (:require [com.stuartsierra.component :as component])
  (:import (com.zaxxer.hikari HikariDataSource)))

(def dbtype-list {:oracle {:classname "oracle.jdbc.OracleDriver"
                           :url-ini "jdbc:oracle:thin:@"}
                 :postgresql {:classname "org.postgresql.Drive"
                              :url-ini "jdbc:postgresql://"}})

(defn pool
  [{:keys [host port db user password dbtype]}]
  {:pre [(string? host)
         (string? port)
         (string? db)
         (string? user)
         (string? password)
         (keyword? dbtype)]}
  (let [l-dbtype (dbtype dbtype-list)]
    (doto (HikariDataSource.)
      (.setDriverClassName (:classname l-dbtype))
      (.setJdbcUrl (str (:url-ini l-dbtype) host ":" port "/" db))
      (.setUsername user)
      (.setPassword password)
      (.setMinimumIdle 10))))


(defrecord Db [config]
  component/Lifecycle
  (start [this]
    (println ";; ...Starting compoment Dd")
    (let [spec-db (get-in config [:config :database])
          conn (pool spec-db)]
      (assoc this :connection conn)))
  (stop [this]
    (println ";; ...Stopping compoment Dd")
    (let [conn (:connection this)]
      (.close conn)
      (assoc this :connection nil))))

(defn new-db
  []
  (map->Db {}))