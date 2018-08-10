package app

import "os"
import "github.com/gin-gonic/gin"

var router *gin.Engine

func Start() {
	port := os.Getenv("PORT")
	router := gin.New()

	configureRouter()

	router.Run(":" + port)
}

func configureRouter() {
	mapUrlsToControllers()

	router.Use(gin.Logger())
	router.LoadHTMLGlob("templates/**/*.tmpl.html")
	router.Static("/static", "static")
}
