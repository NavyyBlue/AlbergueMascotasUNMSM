document.addEventListener("DOMContentLoaded", function() {
    const cardContainer = document.getElementById("card-container");
    const paginationContainer = document.getElementById("pagination");
    const cardsPerPage = 9;
    const totalCards = 30;
    let currentPage = 1;

function showCards(page) {
    cardContainer.innerHTML = "";
    for (let i = (page - 1) * cardsPerPage + 1; i <= Math.min(page * cardsPerPage, totalCards); i++) {
        const card = document.createElement("div");
        card.className = "card";
        const imageNumber = i >= 10 ? i : i;
        card.style.backgroundImage = `url('image${imageNumber}.png')`;
        cardContainer.appendChild(card);
    }
}
    

    function updatePagination() {
        paginationContainer.innerHTML = "";
        const totalPages = Math.ceil(totalCards / cardsPerPage);
        for (let i = 1; i <= totalPages; i++) {
            const button = document.createElement("button");
            button.className = "pagination-button";
            button.textContent = i;
            button.addEventListener("click", function() {
                currentPage = i;
                showCards(currentPage);
                updatePaginationButtons();
            });
            paginationContainer.appendChild(button);
        }
    }

    function updatePaginationButtons() {
        const buttons = document.querySelectorAll(".pagination-button");
        buttons.forEach((button, index) => {
            if (index + 1 === currentPage) {
                button.classList.add("active");
            } else {
                button.classList.remove("active");
            }
        });
    }

    showCards(currentPage);
    updatePagination();

    const prevBtn = document.createElement("button");
    prevBtn.className = "pagination-button";
    prevBtn.innerHTML = "&#9665;"; // Unicode para el icono de flecha izquierda
    prevBtn.disabled = true;
    prevBtn.addEventListener("click", function() {
        if (currentPage > 1) {
            currentPage--;
            showCards(currentPage);
            updatePaginationButtons();
        }
    });
    paginationContainer.insertBefore(prevBtn, paginationContainer.firstChild);

    const nextBtn = document.createElement("button");
    nextBtn.className = "pagination-button";
    nextBtn.innerHTML = "&#9655;"; // Unicode para el icono de flecha derecha
    nextBtn.addEventListener("click", function() {
        if (currentPage < Math.ceil(totalCards / cardsPerPage)) {
            currentPage++;
            showCards(currentPage);
            updatePaginationButtons();
        }
    });
    paginationContainer.appendChild(nextBtn);
});
