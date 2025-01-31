document.addEventListener("DOMContentLoaded", function() {
    // Get the URL parameters
    const queryString = window.location.search;
    const urlParams = new URLSearchParams(queryString);
  
    // Get the 'price' parameter value from the URL
    const price = urlParams.get('price');
    console.log(price)
    // Use the 'price' value as needed (e.g., display it on the page)
    if (price) {
      document.getElementById("price_amount").textContent = " $ " + price;
    }

    const form = document.getElementById('payment-form');
    const responseDiv = document.getElementById('response');

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const credit_card = document.getElementById('credit-card').value;
        const cvv = document.getElementById('cvv').value;
        const month = document.getElementById('expiry-month').value;
        const year = document.getElementById('expiry-year').value;
        const address = document.getElementById('address').value;


        const formData = new FormData(form);
        // formData.append('credit_card', credit_card);
        // formData.append('cvv', cvv);
        // formData.append('expiry_month', month);
        // formData.append('expiry_year', year);
        // formData.append('address', address);
        formData.append('price', price);
        console.log(credit_card);
        fetch('/social-network/transaction', {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            if (data.transtatus) {
                responseDiv.innerText = 'Payment is successful! User upgraded';
            } else {
                responseDiv.innerText = 'Payment failed! Try again after some time.';
            }
        })
        .catch(error => {
            responseDiv.innerText = 'Error: ' + error.message;
        });
    });
  });