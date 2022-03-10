(ns hospital2.aula2
  (:use clojure.pprint))

;(defrecord Paciente [id, nome, nascimento])

; Paciente Plano de Saúde ===> + plano de saúde
; Paciente Particular ===> + 0

; caminho horripilante com provaveis problemas e tipo 2^n
;(defrecord PacientePlanoDeSaude HERDA Paciente [plano])

(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacientePlanoDeSaude [id, nome, nascimento, plano])

; REGRAS DIFERENTES PARA TIPOS DIFERENTES (lembra o padrão strategy)
;deve-assinar-pre-autorizacao?
; Particular ==> valor >= 50
; PlanoDeSaude ==> procedimento NAO esta no plano

; VANTAGEM tudo no mesmo lugar
; DESVANTAGEM tudo no mesmo lugar
;(defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
;  (if (= PacienteParticular (type paciente))
;    (>= valor 50)
;    ; assumindo que existe os dois tipos
;    (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))


(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]))

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacientePlanoDeSaude
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))
; contains? => verifica o índice... e índice voce fica dependendo da estrutura de dados

;(defrecord PacientePlanoDeSaude
;  [id, nome, nascimento, plano]
;  Cobravel
;  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
;    (let [plano (:plano paciente)]
;      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15, "Guilherme", "18/9/1981")
      plano (->PacientePlanoDeSaude 15, "Guilherme", "18/9/1981", [:raio-x, :ultassom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raio-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raio-x, 49999))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 49999))
  )

; uma variacao baseada na palestra a seguir, mas com extend-type e gregorian calendar
; from Sean Devlin's talk on protocols at Clojure Conj
(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))

