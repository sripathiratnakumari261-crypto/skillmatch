async function findMatches() {

    const email =
        document.getElementById("email").value.trim();

    const errorMessage =
        document.getElementById("errorMessage");

    const results =
        document.getElementById("results");

    const jobs =
        document.getElementById("jobs");

    const users =
        document.getElementById("users");

    const jobScores =
        document.getElementById("jobScores");


    // Clear previous error
    errorMessage.textContent = "";


    // Validate email
    if (!email) {

        errorMessage.textContent =
            "Please enter your email.";

        return;
    }


    // Show results section
    results.classList.remove("hidden");


    // Loading states
    jobs.innerHTML = "Loading...";
    users.innerHTML = "Loading...";
    jobScores.innerHTML = "Loading...";


    try {

        // 1. Get recommended jobs
        const jobsResponse =
            await fetch(
                `/api/recommendations?email=${encodeURIComponent(email)}`
            );


        // 2. Get similar users
        const usersResponse =
            await fetch(
                `/api/similar-users?email=${encodeURIComponent(email)}`
            );


        // 3. Get job match scores
        const scoresResponse =
            await fetch(
                `/api/job-match-scores?email=${encodeURIComponent(email)}`
            );


        // Check all API responses
        if (
            !jobsResponse.ok ||
            !usersResponse.ok ||
            !scoresResponse.ok
        ) {

            throw new Error(
                "Unable to load recommendations."
            );
        }


        // Convert responses to JSON
        const recommendedJobs =
            await jobsResponse.json();

        const similarUsers =
            await usersResponse.json();

        const jobScoresData =
            await scoresResponse.json();


        // Display results
        displayJobs(recommendedJobs);

        displayUsers(similarUsers);

        displayJobScores(jobScoresData);


    } catch (error) {

        console.error(
            "Error loading SkillMatch data:",
            error
        );


        jobs.innerHTML =
            "Unable to load job recommendations.";

        users.innerHTML =
            "Unable to load similar users.";

        jobScores.innerHTML =
            "Unable to load job match scores.";


        errorMessage.textContent =
            "Something went wrong. Please try again.";
    }
}


/* =========================================
   DISPLAY RECOMMENDED JOBS
   ========================================= */

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

        item.className =
            "result-item";

        item.textContent =
            job;

        container.appendChild(item);
    });
}


/* =========================================
   DISPLAY SIMILAR USERS
   ========================================= */

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

        item.className =
            "result-item";

        item.textContent =
            user;

        container.appendChild(item);
    });
}


/* =========================================
   DISPLAY JOB MATCH SCORES
   ========================================= */

function displayJobScores(scores) {

    const container =
        document.getElementById("jobScores");


    if (!container) {

        console.error(
            "jobScores element not found in index.html"
        );

        return;
    }


    if (!scores || scores.length === 0) {

        container.innerHTML =
            '<div class="result-item">No job match scores found.</div>';

        return;
    }


    container.innerHTML = "";


    scores.forEach(score => {

        const item =
            document.createElement("div");

        item.className =
            "result-item";

        item.textContent =
            score;

        container.appendChild(item);
    });
}