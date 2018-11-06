package controllers

import (
	"github.com/gin-gonic/gin"
	"net/http"
	"github.com/bhaslop/bolaso/app/service"
	"fmt"
	"github.com/bhaslop/bolaso/app/dao"
)

func UserHandler(c *gin.Context) {

	model := gin.H{}

	if IsAuthenticated(c) {
		model["User"] = service.GetUserFromSession(c)
		model["Authenticated"] = true
	} else {
		model["Authenticated"] = false
	}

	fmt.Println(dao.GetPlayer(model["user"].email))

	c.HTML(http.StatusOK, "user/index", model)
}