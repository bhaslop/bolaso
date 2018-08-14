package controllers

import (
	"github.com/gin-gonic/gin"
	"github.com/gin-contrib/sessions"
	"net/http"
)

func UserHandler(c *gin.Context) {
	session := sessions.Default(c)

	c.HTML(http.StatusOK, "user/index", session.Get("profile"))
}