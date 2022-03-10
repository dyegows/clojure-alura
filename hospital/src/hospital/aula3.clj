(ns hospital.aula3
  (:use [clojure pprint])
  (:require [hospital.logic :as h.logic]
            [hospital.model :as h.model]))

; símbolo que qq thread  que acessar esse namespace vai ter acesso a ele com o valor padrão guilherme
(def nome "Guilherme")

; redefinir o símbolo (refiz o binding)
(def nome "redefinir")

(let [nome "guilherme"]
  ; coisa 1
  ; coisa 2
  (println nome)
  ; não estou refazendo o binding
  ; SHADOWING
  (let [nome "daniela"]
    ;coisa 3
    ;coisa 4
    (println nome))
  (println nome))

(defn testa-atomao []
  (let [hospital-silveira (atom {:espera h.model/fila-vazia})]
    (println hospital-silveira)
    (pprint hospital-silveira)
    (pprint (deref hospital-silveira))
    (pprint @hospital-silveira)

    ; não é assim que altero o conteúdo dentro do átomo
    (pprint (assoc @hospital-silveira :laboratorio1 h.model/fila-vazia))
    (pprint @hospital-silveira)

    ; essa é (uma das) maneira de alterar conteúdo dentro de um átomo
    (swap! hospital-silveira assoc :laboratorio1 h.model/fila-vazia)
    (pprint @hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio2 h.model/fila-vazia)
    (pprint @hospital-silveira)

    ; update tradicional imutável, com dereferencia, que nao trara efeito
    (update @hospital-silveira :laboratorio1 conj "111")

    ;indo pra swap
    (swap! hospital-silveira update :laboratorio1 conj "111")
    (pprint @hospital-silveira)
    ))

;(testa-atomao)

(defn chega-em-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 8000)
                       (pprint hospital))))))

; forçando situações de retry
;(simula-um-dia-em-paralelo)



(defn chega-sem-malvado! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))

(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "111"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "222"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "333"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "444"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "555"))))
    (.start (Thread. (fn [] (chega-sem-malvado! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 1000)
                       (pprint hospital))))))

; sem forçar a situação de retry (busy retry), pode acontecer, mas pode não acontecer.
(simula-um-dia-em-paralelo)



















































