package service

import (
	"github.com/gin-gonic/gin"
	"github.com/gin-contrib/sessions"
)

func GetUserFromSession(c *gin.Context) interface{} {
	session := sessions.Default(c)

	return session.Get("profile")
}