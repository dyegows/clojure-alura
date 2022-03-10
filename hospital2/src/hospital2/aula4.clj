(ns hospital2.aula4
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento, situacao])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, situacao, plano])

; deve-assinar-pre-autorizacao?
; >= 50; plano

(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(defn nao-eh-urgente? [paciente]
  (not= :urgente (:situacao paciente :normal)))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (and (>= valor 50) (nao-eh-urgente? paciente))))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (and (not (some #(= % procedimento) plano)) (nao-eh-urgente? paciente)))))


(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981", :normal)
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", :normal, [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 49999))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 49999)))

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981", :urgente)
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", :urgente, [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 49999))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 49999)))


; não se coloca multi no final, só para fins didáticos
(defmulti deve-assinar-pre-autorizacao-multi? class)
(defmethod deve-assinar-pre-autorizacao-multi? PacienteParticular [paciente]
  (println "invocando paciente particular")
  true)
(defmethod deve-assinar-pre-autorizacao-multi? PacientePlanoDeSaude [paciente]
  (println "invocando paciente plano de saúde")
  false)

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981", :urgente)
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", :urgente, [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao-multi? particular))
  (pprint (deve-assinar-pre-autorizacao-multi? plano)))

; explorando como funciona a funcao que define a estrategia de um defmulti
(defn minha-funcao [p]
  (println p)
  (class p))

(defmulti multi-teste minha-funcao)
;(multi-teste "guilherme")
;(multi-teste :guilherme)

; pedido { :paciente paciente, :valor valor, :procedimento procedimento }

; um pouco feio pois estou misturando keyword e classe como chave
(defn tipo-de-autorizador [pedido]
  (let [paciente (:paciente pedido)
        situacao (:situacao paciente)
        urgencia? (= :urgente situacao)]
    (if urgencia?
      :sempre-autorizado
      (class paciente))))

(defmulti deve-assinar-pre-autorizacao-do-pedido? tipo-de-autorizador)

(defmethod deve-assinar-pre-autorizacao-do-pedido? :sempre-autorizado [pedido]
  false)

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacienteParticular [pedido]
  (>= (:valor pedido 0) 50))

(defmethod deve-assinar-pre-autorizacao-do-pedido? PacientePlanoDeSaude [pedido]
  (not (some #(= % (:procedimento pedido)) (:plano (:paciente pedido)))))

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981", :urgente)
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", :urgente, [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sagnue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sagnue})))

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981", :normal)
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", :normal, [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente particular, :valor 1000, :procedimento :coleta-de-sagnue}))
  (pprint (deve-assinar-pre-autorizacao-do-pedido? {:paciente plano, :valor 1000, :procedimento :coleta-de-sagnue})))







