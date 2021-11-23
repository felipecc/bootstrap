-- :name get-pacientes-internados :?
-- :doc Lista de pacientes internados
select * 
from dbamv.atendime
where tp_atendimento = 'I'
  and data_alta is null

