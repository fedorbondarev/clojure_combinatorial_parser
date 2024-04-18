(load-file "src/main/clojure/expressions.clj")
(load-file "src/main/clojure/parser.clj")

(declare get*expr)

(def operatorsKeys (sort-by (comp - count) (mapv str (keys expressionsOperators))))

(defn sign [c tail]
      (if (#{\- \+} c)
        (cons c tail)
        tail))

(def *digitsStr (+str (+plus (+char "0123456789"))))

(def *number
  (+map
    (comp constant read-string)
    (+str
      (+seqf
        sign
        (+opt (+char "+-"))
        (+map
          #(if (nil? (second %)) (first %) (apply str %))
          (+seq
            *digitsStr
            (+opt
              (+str (+seq
                      (+char ".")
                      *digitsStr)))))))))

(def *variable
  (+map
    variable
    (+str (+plus (+char "xyzXYZ")))))

(defn +string [s]
      (+map
        (partial apply str)
        (apply +seq
               (mapv (comp +char str) s))))

(def *operator
  (+map
    #((symbol %) expressionsOperators)
    (apply
      +or
      (mapv
        +string
        operatorsKeys))))

(def *space (+char " \t\n\r"))
(def *ws (+ignore (+star *space)))

(def *complexExpr
  (+map #(apply (second %) (first %))
        (+seq
          (+ignore (+char "("))
          (+star (delay (get*expr)))
          *operator
          *ws
          (+ignore (+char ")")))))

(def *expr
  (+map
    first
    (+seq
      *ws
      (+or *number *variable *complexExpr)
      *ws)))

(defn get*expr [] *expr)

(def parsePostfix (+parser *expr))
