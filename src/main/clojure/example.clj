(load-file "src/main/clojure/expressionsParser.clj")

(println ((parsePostfix "(1 2 +)") {}))                     ; expected 3
(println ((parsePostfix "(1 x *)") {"x" 8.6}))              ; expected 8.6
(println ((parsePostfix "((0 exp) 4 *)") {}))               ; expected 4
(println ((parsePostfix "(1 2 /)") {}))                     ; expected 0.5
(println ((parsePostfix "((1 2 /) (((3 exp) 4 *) 2 +) +)") {})) ; expected 82.84214769
(println ((parsePostfix "(1 10 9 2 -)") {}))                ; expected -20
(println ((parsePostfix "(1 10 9 2 meansq)") {}))           ; expected 46.5
