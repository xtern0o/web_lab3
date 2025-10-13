document.addEventListener('DOMContentLoaded', () => {
    logo.onload = () => {
        const canvas = document.getElementById('clock-canvas');
        const ctx = canvas.getContext("2d");
        const canvasSize = 300;
        canvas.width = canvasSize;
        canvas.height = canvasSize;

        let radius = canvas.height / 2;

        ctx.translate(radius, radius);
        radius *= 0.9;

        canvas.addEventListener("click", () => {
            audio.play();
        })

        function drawClock() {
            drawFace(ctx, radius);
            drawTicks(ctx, radius);
            drawTime(ctx, radius);
        }

        function drawFace(ctx, radius) {
            ctx.beginPath();
            ctx.arc(0, 0, radius, 0, 2 * Math.PI);
            ctx.fillStyle = 'white';
            ctx.fill();

            ctx.strokeStyle = '#780000';
            ctx.lineWidth = radius * 0.1;
            ctx.stroke();

            ctx.beginPath();
            ctx.arc(0, 0, radius * 0.05, 0, 2 * Math.PI);
            ctx.fillStyle = '#333';
            ctx.fill();

            const logoSize = radius * 0.6;

            const logoWidth = radius * 0.6;
            const aspectRatio = logo.naturalHeight / logo.naturalWidth;
            const logoHeight = logoWidth * aspectRatio;

            ctx.drawImage(logo, -logoSize / 2, -radius * 0.9, logoWidth, logoHeight);
        }

        function drawTicks(ctx, radius) {
            ctx.strokeStyle = '#780000';
            for (let i = 0; i < 60; i++) {
                const angle = (i / 60) * 2 * Math.PI;
                ctx.save();
                ctx.rotate(angle);

                ctx.beginPath();
                if (i % 5 === 0) {
                    ctx.lineWidth = radius * 0.04;
                    ctx.moveTo(0, -radius * 0.9);
                } else {
                    ctx.lineWidth = radius * 0.02;
                    ctx.moveTo(0, -radius * 0.95);
                }

                ctx.lineTo(0, -radius);
                ctx.stroke();

                ctx.restore();
            }
        }

        drawClock();
    }


});