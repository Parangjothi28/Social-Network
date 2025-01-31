window.onload = function() {
    fetch('/social-network/usertype')
        .then(response => response.json())
        .then(data => {
            if(data.authenticated){
                console.log("User",data.user);
                if(data.user.userType=="BRAND"){
                    const brandmessage = $('.brand-only'); // Assuming you have an element with id "posts-list"
                    const item = `<a href="html/adspage.html" class="w-100 btn btn-lg btn-primary">Post an AD? Click here!</a>`
                    brandmessage.append(item);
                }
            }
            else{
                window.location.href = '/social-network/html/login.html';
            }
        })
        .catch(error => console.error('Error fetching posts:', error));
};

document.addEventListener('DOMContentLoaded', function () {
    const form = document.getElementById('profile-form');
    const responseDiv = document.getElementById('response');

    form.addEventListener('submit', function (e) {
        e.preventDefault();

        const name = document.getElementById('name').value;
        const full_name = document.getElementById('full-name').value;
        const dob = document.getElementById('dob').value;
        const image = document.getElementById('image').files[0];

        if (!name || !full_name || !dob || !image) {
            alert('Please fill in all the fields.');
            return;
        }
        
        const formData = new FormData();
        formData.append('name', name);
        formData.append('full_name', full_name);
        formData.append('dob', dob);
        console.log(full_name);
        fetch('/social-network/profile', {
            method: 'POST',
            body: formData,
        })
        .then(response => response.json())
        .then(data => {
            if (data.profilestatus) {
                // The user is authenticated and status is true
                responseDiv.innerText = 'Profile Updated';
            } else {
                // User is not authorized to publish ads
                responseDiv.innerText = 'Profile Update Failed!';
            }
        })
        .catch(error => {
            responseDiv.innerText = 'Error: ' + error.message;
        });
    });
});