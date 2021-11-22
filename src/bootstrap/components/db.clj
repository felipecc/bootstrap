(ns bootstrap.components.db
  (:require [com.stuartsierra.component :as component])
  (import (comm.zaxxer.hikari HikariDataSource)))


(defn pool-pg
  [{:keys [host port db user password]}]
  {:pre [(string? host)
         (string? port)
         (string? db)
         (string? user)
         (string? password)]}
  (doto (HikariDataSource.)
    (.setDriverClassName "org.postgresql.Drive")
    (.setJdbcUrl (str "jdbc:postgresql://" host ":" port "/" db))
    (.setUsername user)
    (.setPassword password)
    (.setMinimumIdle 10)))


(defrecord Db [config]
  component/Lifecycle
  (start [this]
    (println ";; ...Starting compoment Dd")
    (let [spec-db (get-in config [:config :database])
          conn (pool-pg spec-db)]
      (assoc this :connection conn)))
  (stop [this]
    (println ";; ...Stopping compoment Dd")
    (let [conn (:connection this)]
      (.close conn)
      (assoc this :connection nil))))

(defn new-db
  []
  (map->Db {}))