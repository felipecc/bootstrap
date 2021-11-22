(ns bootstrap.components.config
  (:require [com.startuartsierra.component :as component]
            [cprop.core :refer [load-config]]))

(defrecord Config [config]
  component/Lifecycle
  (start [this]
    (println ";; ... Iniciando o component de config")
    (assoc this :config (load-config)))
  (stop [this]
    (println ";; ... Iniciando o component de config")
    (assoc this :config nil)))

(defn new-config
  []
  (map->Config {}))