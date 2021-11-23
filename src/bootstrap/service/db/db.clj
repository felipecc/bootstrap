(ns bootstrap.service.db.db
  (:require [hugsql.core :as husql]))

(husql/def-db-fns "bootstrap/service/db/sql/atendimentos.sql")
(husql/def-sqlvec-fns "bootstrap/service/db/sql/atendimentos.sql")