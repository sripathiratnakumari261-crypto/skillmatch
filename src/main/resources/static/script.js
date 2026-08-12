async function findMatches() {

    const email = document.getElementById("email").value.trim();

    const errorMessage =
        document.getElementById("errorMessage");

    const results =
        document.getElementById("results");

    const jobs =
        document.getElementById("jobs");

    const users =
        document.getElementById("users");


    errorMessage.textContent = "";


    if (!email) {

        errorMessage.textContent =
            "Please enter your email.";

        return;
    }


    results.classList.remove("hidden");

    jobs.innerHTML = "Loading...";
    users.innerHTML = "Loading...";


    try {

        const jobsResponse =
            await fetch(
                `/api/recommendations?email=${encodeURIComponent(email)}`
            );

        const usersResponse =
            await fetch(
                `/api/similar-users?email=${encodeURIComponent(email)}`
            );


        if (!jobsResponse.ok || !usersResponse.ok) {

            throw new Error(
                "Unable to load recommendations."
            );
        }


        const recommendedJobs =
            await jobsResponse.json();

        const similarUsers =
            await usersResponse.json();


        displayJobs(recommendedJobs);

        displayUsers(similarUsers);


    } catch (error) {

        jobs.innerHTML =
            "Unable to load job recommendations.";

        users.innerHTML =
            "Unable to load similar users.";

        errorMessage.textContent =
            "Something went wrong. Please try again.";

        console.error(error);
    }
}


function displayJobs(jobs) {

    const container =
        document.getElementById("jobs");


    if (!jobs || jobs.length === 0) {

        container.innerHTML =
            '<div class="result-item">No matching jobs found.</div>';

        return;
    }


    container.innerHTML = "";


    jobs.forEach(job => {

        const item =
            document.createElement("div");

        item.className = "result-item";

        item.textContent = job;

        container.appendChild(item);
    });
}


function displayUsers(users) {

    const container =
        document.getElementById("users");


    if (!users || users.length === 0) {

        container.innerHTML =
            '<div class="result-item">No similar users found.</div>';

        return;
    }


    container.innerHTML = "";


    users.forEach(user => {

        const item =
            document.createElement("div");

        item.className = "result-item";

        item.textContent = user;

        container.appendChild(item);
    });
}