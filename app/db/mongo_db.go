package db

import (
	"github.com/mongodb/mongo-go-driver/mongo"
	"os"
	"context"
)

var (
	client *mongo.Client
	matchesDB *mongo.Database
)

func init() {
	client, _ = mongo.Connect(context.Background(), os.Getenv("MONGODB_URI"), nil)
	matchesDB = client.Database("matches")
}

func GetCollection(collection string) *mongo.Collection{
	return matchesDB.Collection(collection)
}
