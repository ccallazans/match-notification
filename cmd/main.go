package main

import (
	"log"
	"net/http"
	"time"

	"github.com/ccallazans/match-notification/config"
	"github.com/ccallazans/match-notification/internal/api"
	"github.com/joho/godotenv"
)

func main() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file", err)
	}

	db := config.NewPostgresConn()

	router := api.NewApi(db)
	server := &http.Server{
		Addr:         ":8080",
		Handler:      router,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
	}

	err = server.ListenAndServe()
	if err != nil {
		log.Fatal(err)
	}
}
