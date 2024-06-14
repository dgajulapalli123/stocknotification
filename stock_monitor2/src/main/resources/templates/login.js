document.getElementById('login-form').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent form submission

    // Fetch username and password values
    var username = document.getElementById('username').value;
    var password = document.getElementById('password').value;

    // Here you can add validation logic if needed

    // Redirect user to another page (for example, dashboard.html)
    window.location.href = 'index.html';
});