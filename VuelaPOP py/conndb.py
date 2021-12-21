import psycopg2

conexion = psycopg2.connect(host="easybyte.club", database="VuelaPOP", user="javaconnect", password="conndb@Servo2021*", port="2224")

cursor = conexion.cursor()

cursor.execute( "SELECT * FROM pasajeros" )

resultados = cursor.fetchall()

print("id          ", "codigo_vuelo    ", "tipo_plaza  ", "fumador ")

for row in resultados :
    id = row[0]
    codigo_vuelo = row[1]
    tipo_plaza = row[2]
    if row[3] == False:
        fumador = "No"
    else:
        fumador = "Sí"
    print(id, "  ", codigo_vuelo, "      ", tipo_plaza, "        ", fumador)