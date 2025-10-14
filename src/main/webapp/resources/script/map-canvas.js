const canvas = document.querySelector('#map-canvas');
const ctx = canvas.getContext("2d");
const width = canvas.width;
const height = canvas.height;
const centerX = width / 2;
const centerY = height / 2;

const one = 50;

function handleRChange(RSelectedRaw) {
    const selectedRJson = JSON.parse(RSelectedRaw);
    drawFigureOnCanvas(selectedRJson);
}

function getRadians(degrees) {
    return (Math.PI / 180) * degrees;
}

function drawCoordinateSystem() {
    const canvas = document.getElementById('map-canvas');
    const ctx = canvas.getContext('2d');

    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const axisColor = '#333333';
    const arrowSize = 10;

    ctx.clearRect(0, 0, width, height);

    // оси координат
    ctx.strokeStyle = axisColor;
    ctx.lineWidth = 2;

    // ось х
    ctx.beginPath();
    ctx.moveTo(0, centerY);
    ctx.lineTo(width, centerY);
    ctx.stroke();

    // ось у
    ctx.beginPath();
    ctx.moveTo(centerX, 0);
    ctx.lineTo(centerX, height);
    ctx.stroke();

    // стрелка х
    ctx.beginPath();
    ctx.moveTo(width - arrowSize, centerY - arrowSize/2);
    ctx.lineTo(width, centerY);
    ctx.lineTo(width - arrowSize, centerY + arrowSize/2);
    ctx.stroke();

    // стрелка у
    ctx.beginPath();
    ctx.moveTo(centerX - arrowSize/2, arrowSize);
    ctx.lineTo(centerX, 0);
    ctx.lineTo(centerX + arrowSize/2, arrowSize);
    ctx.stroke();

    // и подписать курьером)
    ctx.fillStyle = axisColor;
    ctx.font = '14px Courier New';
    ctx.fillText('x', width - 15, centerY - 10);
    ctx.fillText('y', centerX + 10, 15);
    ctx.fillText('0', centerX + 5, centerY - 5);
}


function drawFigureOnCanvas(selectedRJson) {
    const width = canvas.width;
    const height = canvas.height;
    const centerX = width / 2;
    const centerY = height / 2;
    const areaColor = "rgba(0, 48, 73, 0.4)"

    refreshCanvas();

    selectedRJson.forEach(val => {
        let rValue = parseFloat(val);

        ctx.fillStyle = areaColor;

        ctx.fillRect(centerX - rValue * one, centerY - rValue * one, rValue * one, rValue * one);

        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX, centerY - rValue * one / 2);
        ctx.lineTo(centerX + rValue * one / 2, centerY);
        ctx.closePath();
        ctx.fill();

        ctx.beginPath();
        ctx.arc(centerX, centerY, rValue * one / 2, getRadians(0), getRadians(90));
        ctx.lineTo(centerX, centerY);
        ctx.closePath();
        ctx.fill();
    });

    // drawAllDots();

}


function refreshCanvas() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    drawCoordinateSystem();
    // drawAllDots();
}

refreshCanvas();
