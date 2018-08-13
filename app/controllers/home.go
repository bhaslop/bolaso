package controllers

import "github.com/gin-gonic/gin"

func GetHome(c *gin.Context) {
	c.HTML(200, "home/index", nil)
}
