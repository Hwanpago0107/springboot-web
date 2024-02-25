function getCookie(key) {
    var result = null;
    var cookie = document.cookie.split(";");
    cookie.some(function (item) {
        item = item.replace(" ", "");

        var dic = item.split("=");

        if (key == dic[0]) {
            result = dic[1];
            return true;
        }
    });

    return result;
}

function httpRequest(method, url, body, success, fail) {
    let email = document.getElementById("hidden_role").value;
    if (email == "ROLE_GUEST") {
        alert("로그인 해주시기 바랍니다.");
        location.replace("/login");
        return;
    }

    fetch(url, {
        method: method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
                "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                     "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then((res) => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((result) => {
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequest(method, url, body, success, fail);
                })
                .catch((error) => fail());
        } else {
            return fail();
        }
    });
}

function httpRequestWithMF(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            // "Content-Type": "application/json",
            //"Content-Type": "multipart/form-data",
        },
        body: body,
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            return success(response.body);
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json",
                    //"Content-Type": "multipart/form-data",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then((res) => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((result) => {
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequestWithMF(method, url, body, success, fail);
                })
                .catch((error) => fail());
        } else {
            return fail();
        }
    });
}

function ajaxHttpRequest(method, url, body, success, fail) {
    $.ajax({
        type : method,
        url : url,
        data : JSON.stringify(body),
        dataType: "json",
        contentType:"application/json;charset=UTF-8",
        async : false,
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Content-Type", "application/json");
            xhr.setRequestHeader("Authorization", "Bearer " + localStorage.getItem("access_token"));
        },
        success : function(data, status) {
            if (res.status === 200 || res.status === 201) {
                return success();
            }
            const refresh_token = getCookie("refresh_token");
            if (response.status === 401 && refresh_token) {
                fetch("/api/token", {
                    method: "POST",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("access_token"),
                        "Content-Type": "application/json",
                        //"Content-Type": "multipart/form-data",
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie("refresh_token"),
                    }),
                })
                    .then((res) => {
                        if (res.ok) {
                            return res.json();
                        }
                    })
                    .then((result) => {
                        localStorage.setItem("access_token", result.accessToken);
                        httpRequestWithMF(method, url, body, success, fail);
                    })
                    .catch((error) => fail());
            }
        },
        error : function(res){
            console.log(res);
            return fail();
        }
    });
}

function httpRequestMail(method, url, body, success, fail) {
    fetch(url, {
        method: method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
    }).then((response) => {
        if (response.status === 200 || response.status === 201) {
            sleep(5000);
            return success();
        }
        const refresh_token = getCookie("refresh_token");
        if (response.status === 401 && refresh_token) {
            fetch("/api/token", {
                method: "POST",
                headers: {
                    Authorization: "Bearer " + localStorage.getItem("access_token"),
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({
                    refreshToken: getCookie("refresh_token"),
                }),
            })
                .then((res) => {
                    if (res.ok) {
                        return res.json();
                    }
                })
                .then((result) => {
                    localStorage.setItem("access_token", result.accessToken);
                    httpRequestMail(method, url, body, success, fail);
                })
                .catch((error) => fail());
        } else {
            return fail();
        }
    });
    sleep(5000);
}

function sleep(ms) {
    const wakeUpTime = Date.now() + ms;
    while (Date.now() < wakeUpTime) {}
}

function httpRequestBody(method, url, body, success, fail) {
    let email = document.getElementById("hidden_role").value;
    if (email == "ROLE_GUEST") {
        alert("로그인 해주시기 바랍니다.");
        location.replace("/login");
        return;
    }

    fetch(url, {
        method: method,
        headers: {
            Authorization: "Bearer " + localStorage.getItem("access_token"),
            "Content-Type": "application/json",
        },
        body: body,
    }).then(res =>
        res.json().then(data => ({
                data: data,
                status: res.status
            })
        ).then(response => {
            if (response.status === 200 || response.status === 201) {
                return success(response.data);
            }
            const refresh_token = getCookie("refresh_token");
            if (response.status === 401 && refresh_token) {
                fetch("/api/token", {
                    method: "POST",
                    headers: {
                        Authorization: "Bearer " + localStorage.getItem("access_token"),
                        "Content-Type": "application/json",
                    },
                    body: JSON.stringify({
                        refreshToken: getCookie("refresh_token"),
                    }),
                })
                    .then((res) => {
                        if (res.ok) {
                            return res.json();
                        }
                    })
                    .then((result) => {
                        localStorage.setItem("access_token", result.accessToken);
                        httpRequestBody(method, url, body, success, fail);
                    })
                    .catch((error) => fail());
            } else {
                return fail();
            }
        })
    );
}