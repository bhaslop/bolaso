package app

import (
	"os"
	"github.com/gin-gonic/gin"
	"github.com/gin-contrib/sessions"
	"github.com/gin-contrib/sessions/cookie"
	"encoding/gob"
)

var (
	router *gin.Engine
)

func Start() {
	gob.Register(map[string]interface{}{})

	port := os.Getenv("PORT")
	router = gin.New()
	store := cookie.NewStore([]byte("something-here"))

	router.Use(sessions.Sessions("login", store))

	configureRouter()

	router.Run(":" + port)
}

func configureRouter() {
	mapUrlsToControllers()

	router.Use(gin.Logger())
	router.LoadHTMLGlob("app/templates/**/*.tmpl.html")
	router.Static("/static", "app/static")

	static := router.Group("/static")


	static.Use(func(c *gin.Context) {
		c.Header("Cache-Control", "max-age=85400")
		c.Next()
	})
}
