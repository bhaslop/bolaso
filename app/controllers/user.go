package controllers

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"github.com/bhaslop/bolaso/app/service"
)

func UserHandler(c *gin.Context) {

	model := gin.H{}

	if IsAuthenticated(c) {
		model["User"] = service.GetUserFromSession(c)
		model["Authenticated"] = true
	} else {
		model["Authenticated"] = false
	}

	c.HTML(http.StatusOK, "user/index", model)
}