document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('upload-form');
    const responseDiv = document.getElementById('response');

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const duration = document.getElementById('duration').value;
        const image = document.getElementById('image').files[0];

        if (!duration || !image) {
            alert('Please fill in all the fields.');
            return;
        }

        const formData = new FormData();
        formData.append('duration', duration);
        formData.append('image', image);

        fetch('/social-network/postads', {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            if (data.adstatus) {
                // The user is authenticated and status is true
                responseDiv.innerText = 'Ad is stored successfully';
            } else {
                // User is not authorized to publish ads
                responseDiv.innerText = 'User is not authorized to publish ads';
            }
        })
        .catch(error => {
            responseDiv.innerText = 'Error: ' + error.message;
        });
    });
});