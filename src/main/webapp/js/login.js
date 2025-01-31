let switchCtn = document.querySelector("#switch-cnt");
let switchC1 = document.querySelector("#switch-c1");
let switchC2 = document.querySelector("#switch-c2");
let switchCircle = document.querySelectorAll(".switch__circle");
let switchBtn = document.querySelectorAll(".switch-btn");
let aContainer = document.querySelector("#a-container");
let bContainer = document.querySelector("#b-container");
let signInForm = document.querySelector("#b-form"); // Select the sign-in form
let signUpForm = document.querySelector("#a-form"); // Select the sign-up form

let sendRequest = async (url, formData) => {
    try {
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: new URLSearchParams(formData).toString(),
        });

        const data = await response.json();


        if (data.authenticated) {
            window.location.href = "/social-network/html/home.html";
        } else {
            window.location.href = "/social-network/html/failure.html";
        }

//        const htmlResponse = await response.text();
//        document.body.innerHTML = htmlResponse;

    } catch (error) {
        console.error('Error:', error);
    }
};

let changeForm = (e) => {
    // Toggle form elements when switching between sign-in and sign-up
    switchCtn.classList.add("is-gx");
    setTimeout(function () {
        switchCtn.classList.remove("is-gx");
    }, 1500);

    switchCtn.classList.toggle("is-txr");
    switchCircle[0].classList.toggle("is-txr");
    switchCircle[1].classList.toggle("is-txr");

    switchC1.classList.toggle("is-hidden");
    switchC2.classList.toggle("is-hidden");
    aContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-txl");
    bContainer.classList.toggle("is-z200");
};

let mainF = (e) => {
    // Event listener for the sign-in form
    signInForm.addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent the default form submission
        const formData = new FormData(signInForm);
        sendRequest('/social-network/signin', formData); // Adjust the sign-in endpoint
    });

    // Event listener for the sign-up form
    signUpForm.addEventListener("submit", (event) => {
        event.preventDefault(); // Prevent the default form submission
        const formData = new FormData(signUpForm);
        sendRequest('/social-network/signup', formData); // Adjust the sign-up endpoint
    });

    for (var i = 0; i < switchBtn.length; i++)
        switchBtn[i].addEventListener("click", changeForm);
};

window.addEventListener("load", mainF);