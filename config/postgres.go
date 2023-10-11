package config

import (
	"database/sql"
	"log"
	"os"

	_ "github.com/lib/pq"
)

func NewPostgresConn() *sql.DB {
	connString := os.Getenv("DATABASE_URL")

	dbConn, err := sql.Open("postgres", connString)
	if err != nil {
		log.Fatal(err)
	}

	// schemaSQL, err := os.ReadFile("./sql/schema.sql")
	// if err != nil {
	// 	log.Fatal(err)
	// }

	// _, err = dbConn.Exec(string(schemaSQL))
	// if err != nil {
	// 	log.Fatal(err)
	// }

	return dbConn
}
