(ns loja.aula2)

; ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]

(defn conta
  [total-ate-agr elementos]
  (recur (inc total-ate-agr) (rest elementos)))

;(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))

(defn conta
  [total-ate-agr elementos]
  (if (next elementos)
    (recur (inc total-ate-agr) (rest elementos))))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))


(defn conta
  [total-ate-agr elementos]
  ;(println total-ate-agr elementos)
  (if (next elementos)
    (recur (inc total-ate-agr) (rest elementos))
    (inc total-ate-agr)))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0 []))


(defn conta
  [total-ate-agr elementos]
  (if (seq elementos)
    (recur (inc total-ate-agr) (rest elementos))
    total-ate-agr))

(println (conta 0 ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta 0 []))


(defn minha-funcao
  ([parametro1] (println "p1" parametro1))
  ([parametro1 parametro2] (println "p2" parametro1 parametro2)))
(minha-funcao 1)
(minha-funcao 1 2)


(defn conta
  ([elementos]
   (conta 0 elementos))

  ([total-ate-agr elementos]
   (if (seq elementos)
     (recur (inc total-ate-agr) (rest elementos))
     total-ate-agr)))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta []))

; for total-ate-agora 0, elementos-restantes elementos      ; ;  1 next

(defn conta
  [elementos]
  ;(println "antes do loop")
  (loop [total-ate-agora 0
         elementos-restantes elementos]
    (if (seq elementos-restantes)
      (recur (inc total-ate-agora) (next elementos-restantes))
      total-ate-agora)))

(println (conta ["daniela" "guilherme" "carlos" "paulo" "lucia" "ana"]))
(println (conta []))


