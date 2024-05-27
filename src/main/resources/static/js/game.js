let canvas, ctx;
let targets = [];
let currentTargetIndex = 0;
let startTime, intervalId;
let targetCount, totalDisplayedTargets = 0;
let targetsToDraw = 0

let gameInProgress = false;

document.addEventListener('DOMContentLoaded', () => {
    canvas = document.getElementById('can');
    ctx = can.getContext('2d');
    canvas.style.display = 'none';

    const startBtn = document.getElementById('start-btn');
    startBtn.addEventListener('click', startGame);

});

class Target {
    constructor(x, y, radius, canvas, ctx) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.canvas = canvas;
        this.ctx = ctx;
        this.isVisible = true;
    }
    draw() {
        if (this.isVisible) {
            this.ctx.beginPath();
            this.ctx.arc(this.x, this.y, this.radius, 0, Math.PI * 2);
            this.ctx.fillStyle = 'red';
            this.ctx.fill();
            this.ctx.closePath();
        }
    }
    update() {
        this.isVisible = false;
    }
    isClicked(mouseX, mouseY) {
        const dist = Math.sqrt((this.x - mouseX) ** 2 + (this.y - mouseY) ** 2);
        return dist <= this.radius;
    }
}

function rand(min, max) {
    return Math.floor(Math.random() * (max - min + 1) + min);
}

function startGame() {
    if (!gameInProgress) {
        targetCount = parseInt(document.getElementById('element_num').getAttribute('value'));
        startTime = new Date();
        totalDisplayedTargets = 0;
        currentTargetIndex = 0;
        // TODO noneではなく、リスタートにする
        canvas.style.display = 'block'
        document.getElementById('start-btn').innerText = 'Reset Game';
        gameInProgress = true;
        updateElapsedTime();
        intervalId = setInterval(updateElapsedTime, 1000 / 60); //60fps

        canvas.addEventListener('click', handleCanvasClick);
        drawNextBatchOfTargets();
    } else {
        gameInProgress = false;

        
        canvas.style.display = 'none'
        document.getElementById('start-btn').innerText = 'Start Game';

        clearInterval(intervalId); // タイマーのクリア
        totalDisplayedTargets = 0; // 表示された的のリセット
        currentTargetIndex = 0; // 現在の的のインデックスをリセット
        targets = []; // 的の配列をリセット
        ctx.clearRect(0, 0, canvas.width, canvas.height); // キャンバスをクリア
        document.getElementById('time').innerText = '0:00:000'; // 経過時間のリセット
    }
}

function drawNextBatchOfTargets() {
    targetsToDraw = targetCount > 10 ? targetsToDraw + 1 : 1;
    if (totalDisplayedTargets + targetsToDraw > targetCount) {
        targetsToDraw = targetCount - totalDisplayedTargets;
    }
    if (targetsToDraw >= 10) {
        targetsToDraw = 10;
    }

    targets = [];
    for (let i = 0; i < targetsToDraw; i++) {
        const radius = 20;
        const x = rand(radius, canvas.width - radius);
        const y = rand(radius, canvas.height - radius);
        const target = new Target(x, y, radius, canvas, ctx);
        targets.push(target);
    }
    totalDisplayedTargets += targetsToDraw;
    drawTargets();
}

function drawTargets() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);
    targets.forEach(target => target.draw());
}

function handleCanvasClick(event) {
    const rect = canvas.getBoundingClientRect();
    const mouseX = event.clientX - rect.left;
    const mouseY = event.clientY - rect.top;

    // クリックされた位置にある全ての的を検索し、クリックされたものがあれば処理を行う
    const clickedTargets = targets.filter(target => target.isClicked(mouseX, mouseY));

    if (clickedTargets.length > 0) {
        // クリックされた的の全てを更新し、表示を消す
        clickedTargets.forEach(target => target.update());
        // 次の的を表示する
        currentTargetIndex++;
        if (currentTargetIndex >= targets.length) {
            if (totalDisplayedTargets < targetCount) {
                currentTargetIndex = 0;
                drawNextBatchOfTargets();
            } else {
                // ゲーム終了処理
                const endTime = new Date();
                const elapsedTime = endTime - startTime;
                clearInterval(intervalId);
                submitTime(formatElapsedTime(elapsedTime));
            }
        } else {
            drawTargets();
        }
    }
}
function submitTime(elapsedTime) {
    const form = document.createElement('form');
    form.method = 'POST';
    form.action = '/result';

    const input_time = document.createElement('input');
    input_time.type = 'hidden';
    input_time.name = 'time';
    input_time.value = elapsedTime;

    // const input_id = document.createElement('input');
    // input_id.type = 'hidden';
    // input_id.name = 'user.id';
    // input_id.value = document.getElementById("id").getAttribute('value');

    const input_name = document.createElement('input');
    input_name.type = 'hidden';
    input_name.name = 'name';
    input_name.value = document.getElementById("name").getAttribute('value');

    const input_ele_num = document.createElement('input');
    input_ele_num.type = 'hidden';
    input_ele_num.name = 'eleNum';
    input_ele_num.value = targetCount;

    form.appendChild(input_time);
    // form.appendChild(input_id);
    form.appendChild(input_name);
    form.appendChild(input_ele_num);
    document.body.appendChild(form);
    form.submit();
}

// 経過時間表示
function updateElapsedTime() {
    const currentTime = new Date();
    const elapsedTime = currentTime - startTime; // milliseconds
    

    document.getElementById('time').textContent = formatElapsedTime(elapsedTime);
}

function formatElapsedTime(elapsedTime) {
    const minutes = Math.floor(elapsedTime / 60000);
    const seconds = Math.floor((elapsedTime % 60000) / 1000);
    const milliseconds = elapsedTime % 1000;

    return `${minutes}:${seconds.toString().padStart(2, '0')}:${milliseconds.toString().padStart(3, '0')}`;
}