(ns loja.aula5
  (:require [loja.db :as l.db]
            [loja.logic :as l.logic]))

(defn gastou-bastante? [info-do-usuario]
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]

  ;(println "resumo" resumo)
    (println "keep" (keep gastou-bastante? resumo))
    (println "filter" (filter gastou-bastante? resumo)))


(defn gastou-bastante? [info-do-usuario]
  (println "gastou-bastante?" (:usuario-id info-do-usuario))
  (> (:preco-total info-do-usuario) 500))

(let [pedidos (l.db/todos-os-pedidos)
      resumo (l.logic/resumo-por-usuario pedidos)]

  ;Ł(println "resumo" resumo)
  (println "keep" (keep gastou-bastante? resumo)))

(println "vamos isolar...")

(println (range 10))
(println (take 2 (range 100000000000)))
; a sequência não está sendo "gulosa" (eager)
(let [sequencia (range 10000000)]
  (println (take 2 sequencia))
  (println (take 2 sequencia)))                             ; imutabilidade, sequência é a mesma
; está sendo LAZY (preguiçoso)!!!


(defn filtro1 [x]
  (println "filtro 1" x)
  x)

(println (map filtro1 (range 10)))


(defn filtro2 [x]
  (println "filtro 2" x)
  x)

(println (map filtro2 (map filtro1 (range 10))))

; CHUNKS
(->> (range 50)
     (map filtro1)
     (map filtro2)
     println)

(->> (range 50)
     (mapv filtro1)
     (mapv filtro2)
     println)

(->> [0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9]
     (map filtro1)
     (map filtro2)
     println)

; lista ligada foi 100% lazy nesse cenário
(->> '(0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9)
     (map filtro1)
     (map filtro2)
     println)

