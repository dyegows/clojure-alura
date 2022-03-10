(ns hospital2.aula1
  (:use clojure.pprint))

(defn adiciona-paciente
  "Os pacientes são um mapa da seguinte forma { 15 {paciente 15}, 23 {paciente 23} },
  O paciente { :id 15 .... }"
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn testa-uso-de-pacientes []
  (let [pacientes {}
        guilherme {:id 15, :nome "Guilherme", :nascimento "18/9/1981"}
        daniela {:id 20 :nome "Daniela", :nascimento "18/9/1982"}
        paulo {:nome "Paulo", :nascimento "18/10/1983"}]
    (pprint (adiciona-paciente pacientes guilherme))
    (pprint (adiciona-paciente pacientes daniela))
    (pprint (adiciona-paciente pacientes paulo))
    ))

;(testa-uso-de-pacientes)

(defrecord Paciente [id, nome, nascimento])

(println (->Paciente 15 "Guilherme" "18/8/1981"))
(pprint (->Paciente 15 "Guilherme" "18/8/1981"))
(pprint (Paciente. 15 "Guiherme" "18/8/1981"))
(pprint (Paciente. "Guiherme" 15 "18/8/1981"))
(pprint (Paciente. 15 "18/8/1981" "Guiherme"))
(pprint (class (Paciente. 15 "18/8/1981" "Guiherme")))
(pprint (map->Paciente {:id 15, :nome "Guilherme", :nascimento "18/8/1981"}))

(let [guilherme (->Paciente 15 "Guilherme" "18/8/1981")]
  (println (:id guilherme))
  (println (vals guilherme))
  (println (class guilherme))
  (println (record? guilherme))
  (println (.nome guilherme)))

(pprint (map->Paciente {:id 15, :nome "Guilherme", :nascimento "18/8/1981", :rg "222222"}))
;(pprint (Paciente. "18/8/1981" "Guiherme"))
(pprint (Paciente. nil "18/8/1981" "Guiherme"))
(pprint (map->Paciente {:nome "Guilherme", :nascimento "18/8/1981", :rg "222222"}))

(pprint (assoc (Paciente. nil "18/8/1981" "Guiherme") :id 38))
(pprint (class (assoc (Paciente. nil "18/8/1981" "Guiherme") :id 38)))

(println (= (->Paciente 15 "Guilherme" "18/8/1981") (->Paciente 15 "Guilherme" "18/8/1981")))
(println (= (->Paciente 153 "Guilherme" "18/8/1981") (->Paciente 15 "Guilherme" "18/8/1981")))


