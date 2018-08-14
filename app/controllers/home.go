package controllers

import (
	"github.com/gin-gonic/gin"
	"github.com/bhaslop/bolaso/app/service"
)

func GetHome(c *gin.Context) {
	model := make(map[string]interface{})

	if IsAuthenticated(c) {
		model["User"] = service.GetUserFromSession(c)
		model["Authenticated"] = true
	} else {
		model["Authenticated"] = false
	}

	c.HTML(200, "home/index", model)
}
