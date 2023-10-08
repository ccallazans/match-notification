package main

import (
	"log"
	"net/http"
	"time"

	"github.com/ccallazans/match-notification/internal/api"
	"github.com/ccallazans/match-notification/internal/domain/entity"
	"github.com/ccallazans/match-notification/internal/infra/database"

	"github.com/joho/godotenv"
)

func main() {
	err := godotenv.Load()
	if err != nil {
		log.Fatal("Error loading .env file", err)
	}

	db := database.NewPostgresConnection()
	err = db.AutoMigrate(&entity.Notification{}, &entity.Topic{}, &entity.User{})
	if err != nil {
		log.Fatal(err)
	}

	router := api.NewApi(db)
	server := &http.Server{
		Addr:         ":8082",
		Handler:      router,
		ReadTimeout:  10 * time.Second,
		WriteTimeout: 10 * time.Second,
	}

	err = server.ListenAndServe()
	if err != nil {
		log.Fatal(err)
	}
}
