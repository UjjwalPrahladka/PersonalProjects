let bars = [];
let time = 200;
function init(numberOfBars) {
	let container = document.getElementById('main');
	container.innerHTML= "";
	let width = container.clientWidth;
	let height = container.clientHeight;
	let widthOfBar = width / numberOfBars;
	widthOfBar -= 2;
	let prev = 0;
	for(let i = 1; i <= numberOfBars; i++){
		let h = Math.floor(Math.random() * (height - 10)) + 2;
		let newBar = document.createElement('span');
		newBar.id = 'bar' + i;
		newBar.classList.add('bar');
		newBar.style.width = widthOfBar + 'px';
		newBar.style.height = h + 'px';
		newBar.style.marginTop = (height-h) + 'px';
		newBar.style.left = prev + 'px';
		prev += widthOfBar + 2;
		container.appendChild(newBar);
	}
	bars = [];
	for(let i = 1; i <= numberOfBars; i++){
		bars.push(document.getElementById('bar' + i));
	}
}

function activate(bars, i) {
	bars[i].style.backgroundColor = '#ff1818';
}

function deactivate(bars, i) {
	bars[i].style.backgroundColor = '#000080';
}

function fix(bars, i){
	bars[i].style.backgroundColor = '#39ff14';
}

function sleep(ms) {
  return new Promise(resolve => setTimeout(resolve, ms));
}

async function bubbleSort() {
	let len = bars.length;
	for(let i = 0; i < len; i++){
		for(let j = 0; j < len-1-i; j++){
			activate(bars, j);
			activate(bars, j+1);
			if(bars[j].clientHeight > bars[j+1].clientHeight){

				let x = bars[j].style.left;
				let y = bars[j+1].style.left;
				bars[j].style.left = y;
				bars[j+1].style.left = x;
				let temp = bars[j];
				bars[j] = bars[j+1];
				bars[j+1] = temp;


			}
			await sleep(time);
			deactivate(bars, j+1);
			deactivate(bars, j);

		}
		fix(bars, len-i-1);
	}
	for(let i = 0; i < bars.length; i++) fix(bars, i);
}

async function mergeSort(){
	time *= 3;
    let x = document.getElementsByClassName('bar');
	for(let i = 0; i < x.length; i++){
		x[i].style.transitionDuration = (time / 1000) + 's';
	}
   let curr_size;
   let left_start;
   let n = bars.length;
   for (curr_size=1; curr_size<=n-1; curr_size = 2*curr_size){
       for (left_start=0; left_start<n-1; left_start += 2*curr_size){
            let mid = Math.min(left_start + curr_size - 1, n-1);

            let right_end = Math.min(left_start + 2*curr_size - 1, n-1);
            let start = left_start;
         	let end = right_end;
            let i = start;
			let j = mid+1;
			let res = [];
			let leftValues = [];
			for(let k = start; k <= end; k++){
				leftValues.push(bars[k].style.left);
				activate(bars, k);
			}
			while(i <= mid && j <= end){
				if(bars[i].clientHeight <= bars[j].clientHeight){
					res.push(bars[i]);
					i++;
				}
				else{
					res.push(bars[j]);
					j++;
				}
			}
			while(i <= mid){
				res.push(bars[i]);
				i++;
			}
			while(j <= end){
				res.push(bars[j]);
				j++;
			}
			for(let i = 0; i < res.length; i++){
				bars[i+start] = res[i];
				res[i].style.left = leftValues[i];
			}
			await sleep(time);
			for(let k = start; k <= end; k++){
				deactivate(bars, k);
			}
       }
   }
   for(let i = 0; i < bars.length; i++) fix(bars, i);
}

async function quickSort(){
	time *= 3;
    let x = document.getElementsByClassName('bar');
	for(let i = 0; i < x.length; i++){
		x[i].style.transitionDuration = (time / 1000) + 's';
	}
   let stack = [];
   stack.push(0);
   stack.push(bars.length-1);

   while(stack.length > 0){
   		let h = stack[stack.length-1];
   		stack.pop();
   		let l = stack[stack.length-1];
   		stack.pop();
   		let leftValues = [];
		for(let k = l; k <= h; k++){
			leftValues.push(bars[k].style.left);
			activate(bars, k);
		}

   		let x = bars[h].clientHeight;
	    let i = (l - 1);
	    fix(bars, h);
	    for (let j = l; j <= h - 1; j++) {
	        if (bars[j].clientHeight <= x) {
	            i++;
	            let temp = bars[i];
	            bars[i] = bars[j];
	            bars[j] = temp;
	        }
	    }
	    let temp = bars[i+1];
	    bars[i+1] = bars[h];
	    bars[h] = temp;
	    let p = i+1;
	    for(let k = l; k <= h; k++){
	    	bars[k].style.left = leftValues[k-l];
	    }
	    await sleep(time);
	    for(let k = l; k <= h; k++){
	    	if(k != i+1)
				deactivate(bars, k);
		}
   		if (p - 1 > l) {
            stack.push(l);
            stack.push(p-1);
        }
        if (p + 1 < h) {
            stack.push(p+1);
            stack.push(h);
        }
   }
   for(let i = 0; i < bars.length; i++) fix(bars, i);
}

async function selectionSort(){
	let len = bars.length;

	for(let i = 0; i < len; i++){
		let min = i;
		activate(bars, i);
		for(let j = i+1; j < len; j++){
			activate(bars, j);
			await sleep(time);
			if(bars[j].clientHeight < bars[min].clientHeight){
				deactivate(bars, min);
				min = j;
			}
			else{
				deactivate(bars, j);
			}
		}
		if(min != i){
			let x = bars[i].style.left;
			let y = bars[min].style.left;

			bars[i].style.left = y;
			bars[min].style.left = x;
			await sleep(time);
			let temp = bars[i];
			bars[i] = bars[min];
			bars[min] = temp;

		}
		fix(bars, i);
	}
	for(let i = 0; i < bars.length; i++) fix(bars, i);
	
}

async function insertionSort(){
	let n = bars.length;
	for(let i = 1; i < n; i++){
		let j = i-1;
		while(j >= 0 && bars[j].clientHeight > bars[j+1].clientHeight){
			activate(bars, j);
			activate(bars, j+1);
			let x = bars[j].style.left;
			let y = bars[j+1].style.left;
			bars[j].style.left = y;
			bars[j+1].style.left = x;
			let temp = bars[j];
			bars[j] = bars[j+1];
			bars[j+1] = temp;
			await sleep(time);
			deactivate(bars, j);
			deactivate(bars, j+1);
			j--;
		}

	}
	for(let i = 0; i < bars.length; i++) fix(bars, i);
}

function start() {
	let radios = document.getElementsByName('type');

	for (let i = 0, length = radios.length; i < length; i++) {
		if (radios[i].checked) {
			switch(radios[i].value){
				case 'merge': mergeSort(); break;
				case 'quick': quickSort(); break;
				case 'insertion': insertionSort(); break;
				case 'bubble': bubbleSort(); break;
				case 'selection': selectionSort(); break;
			}
		}
	}
}

function reset(){
	window.location.reload();
}

window.addEventListener('load', (event) => {
    init(50);

    let slide = document.getElementById('myRange');

	slide.oninput = function() {
	    init(this.value);
	    time = Math.min(200 * 50 / this.value, 1000);
	    let x = document.getElementsByClassName('bar');
		for(let i = 0; i < x.length; i++){
			x[i].style.transitionDuration = (time / 1000) + 's';
		}
	}
});
