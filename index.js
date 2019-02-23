$(function() {
    bindFunction()
})


function bindFunction() {
    $("#ubaas-table").on("click", ".ubass-right-header-item", function() {
        if ($(this).hasClass("active")) return;
        $(".ubass-right-header-item.active").removeClass("active");
        $(this).addClass("active");
        //$("#iframe").attr("src", "./iframe/html1.html" || $(this).data("src"));
        $("#iframe").attr("src",$(this).data("src"));
    })
    $("#ubaas-slide").on("click", ".click-title", function() {
        var $this = $(this);
        var parent = $this.parent();
        var img = $this.find("img")
        var imgsrc = img.attr("src");
        var activeItem = $(".ubaas-slide-item.active");
        var iframe = $("#iframe");
        if ($this.data("src")) {
        	//alert($this.data("src"));
            addTableCard($this.data("id") || $this.text().match(/\w/g).join(""), $this.data("src"));
            //iframe.attr("src", "./iframe/html1.html" || $this.data("src"));
            iframe.attr("src", $this.data("src"));
            if (!parent.hasClass("active")) {
            	alert("changename");
                removeSlideActive();
                parent.addClass("active");
                img.attr("src", imgsrc.replace(".png", ".1.png"))
            }
            return;
        }
        if (parent.hasClass("active")) {
            parent.removeClass("active");
            img.attr("src", imgsrc.replace(".1.png", ".png"));
            return;
        }
        removeSlideActive()
        parent.addClass("active");
        img.attr("src", imgsrc.replace(".png", ".1.png"));
    })
    $("#ubaas-slide").on("click", ".children-item", function() {
        var $this = $(this);
        var parent = $this.parent();
        var iframe = $("#iframe");
        var src = $this.data("src");
        //var data_src=$this.attr("data-src");
        if (src) {
        	iframe.attr('src',src);
            //iframe.attr("src", "./iframe/html1.html" || src);
            addTableCard($this.data("id") || $this.text().match(/\w/g).join(""), src);
        }
    })
    $("#ubaas-table").on("click", ".close", function(e) {
        e.stopPropagation();
        var $this = $(this);
        var parent = $this.parent(); //li
        if (parent.hasClass("active")) {
            if (parent.next().length > 0) {
                parent.next().click()
            } else {
                parent.prev().click()
            }
        }
        parent.remove();
    })
}

function removeSlideActive() {
    var activeItem = $(".ubaas-slide-item.active");
    if (activeItem.length > 0) {
        activeItem
            .removeClass("active");
        activeItem.find("img").attr("src", activeItem.find("img").attr("src").replace(".1.png", ".png"))
    }
}

function addTableCard(id, src) {
    if ($(`#${id}`).length > 0) {
        $(`#${id}`).click();
        return;
    }
    $(".ubass-right-header-item").removeClass("active");
    $("#ubaas-table").append(`<li class="ubass-right-header-item active" id="${id}" data-src="${src}">${id}<span class="close">x</span></li>`)
}