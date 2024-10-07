// Show the Sign Up form initially
document.getElementById('signup-form').style.display = 'block';

// Function to show the Login form
function showLoginForm() {
    document.getElementById('signup-form').style.display = 'none';
    document.getElementById('login-form').style.display = 'block';
}

// Function to show the Sign Up form
function showSignupForm() {
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('signup-form').style.display = 'block';
}

// Function to handle sign-up process
function signUp(event) {
    event.preventDefault();
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    const confirmPassword = document.getElementById('confirm-password').value;

    if (password === confirmPassword) {
        localStorage.setItem('userEmail', email);
        showProfile(email);
    } else {
        alert('Passwords do not match');
    }
}

// Function to handle log-in process
function logIn(event) {
    event.preventDefault();
    const email = document.getElementById('login-email').value;
    const savedEmail = localStorage.getItem('userEmail');
    
    if (email === savedEmail) {
        showProfile(email);
    } else {
        alert('No account found for this email');
    }
}

// Function to display the profile after login/signup
function showProfile(email) {
    document.getElementById('signup-form').style.display = 'none';
    document.getElementById('login-form').style.display = 'none';
    document.getElementById('profile').style.display = 'block';
    document.getElementById('profile-email').textContent = email;
}

// Function to handle log-out process
function logOut() {
    document.getElementById('profile').style.display = 'none';
    showLoginForm();
}


// 이미지 미리보기 함수
function previewImage(event) {
    const file = event.target.files[0];  // 파일 선택
    const preview = document.getElementById('profile-preview');  // 미리보기 이미지 요소

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            preview.src = e.target.result;  // 이미지를 미리보기 영역에 표시
        };
        reader.readAsDataURL(file);  // 파일을 읽어서 URL로 변환
    }
}

const inputId = document.querySelector("#test1");
const inputPw = document.querySelector("#password");
const loginButton = document.getElementById("loginButton");

// 로그인 버튼 클릭 이벤트 추가
loginButton.addEventListener("click", showAlert);

function showAlert() {
    const id = inputId.value; // 입력된 아이디
    const pw = inputPw.value; // 입력된 비밀번호

    // alert로 아이디와 비밀번호 표시
    alert(`아이디: ${id}\n비밀번호: ${pw}`);
}