package controllers

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"github.com/bhaslop/bolaso/app/service"
)

func UserHandler(c *gin.Context) {
	c.HTML(http.StatusOK, "user/index", service.GetUserFromSession(c))
}