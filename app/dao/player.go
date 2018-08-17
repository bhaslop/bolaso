package dao

import (
	"github.com/mongodb/mongo-go-driver/bson"
	"github.com/bhaslop/bolaso/app/db"
	"context"
	"log"
	"github.com/mongodb/mongo-go-driver/mongo"
)

type Player struct {
	Name string
	Email string
}


func GetPlayer(email string) *Player {
	player := &Player{}

	err := playerCollection().FindOne(context.Background(), bson.NewDocument(bson.EC.String("email", email))).Decode(player)

	if err != nil {
		log.Fatal(err)
	}

	return player
}

func SetPlayer(email string, name string) {
	player := &Player{name, email}

	playerCollection().InsertOne(context.Background(), player)
}


func playerCollection() *mongo.Collection {
	return db.GetCollection("players")
}
