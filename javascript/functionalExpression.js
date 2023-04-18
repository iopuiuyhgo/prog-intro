const vars = {
    "x":0,
    "y":1,
    "z":2,
}

const arg = (f) => (...args) => [...args].reduce(f, 0)
const ultimate = f => (...args) => (...argv) => f(...args.map(arg => arg(...argv)))

const add = ultimate((a, b) => a + b)
const subtract = ultimate((a, b) => a - b)
const multiply = ultimate((a, b) => a * b)
const divide = ultimate((a, b) => a / b)

const sin = ultimate(a => Math.sin(a))
const cos = ultimate(a => Math.cos(a))

const sinh = ultimate(a => Math.sinh(a))
const cosh = ultimate(a => Math.cosh(a))

const floor = ultimate(a => Math.floor(a))
const ceil = ultimate(a => Math.ceil(a))

const negate = ultimate(a => -a)

const argMin3 = ultimate(arg((mInd, cVal, cInd, arr) => {return (cVal < arr[mInd]) ? cInd : mInd; }))
const argMax3 = ultimate(arg((mInd, cVal, cInd, arr) => {return (cVal > arr[mInd]) ? cInd : mInd; }))
const argMin5 = ultimate(arg((mInd, cVal, cInd, arr) => {return (cVal < arr[mInd]) ? cInd : mInd; }))
const argMax5 = ultimate(arg((mInd, cVal, cInd, arr) => {return (cVal > arr[mInd]) ? cInd : mInd; }))

const madd = ultimate((a, b, c) => a * b + c)
const one = () => 1
const two = () => 2
const cnst = a => () => Number(a)
const variable = (a) => (...args) => {
    return args[vars[a]]
}
const func = {
    "+":[add, 2],
    "-":[subtract, 2],
    "*":[multiply, 2],
    "/":[divide, 2],
    "negate":[negate, 1],
    "one":[one, 0],
    "two":[two, 0],
    "sin":[sin, 1],
    "cos":[cos, 1],
    "sinh":[sinh, 1],
    "cosh":[cosh, 1],
    "_":[floor, 1],
    "^":[ceil, 1],
    "argMin3":[argMin3, 3],
    "argMax3":[argMax3, 3],
    "argMin5":[argMin5, 5],
    "argMax5":[argMax5, 5],
    "*+":[madd, 3]
}
function parseFunc(stack, func) {
    if (func[1] === 0) { return func[0]; }
    const tmp = stack.splice(stack.length - func[1], stack.length)
    return func[0](...tmp)
}
function parse(s) {
    s = s.split(" ").filter(ch => ch.length > 0);
    let stack = [];
    for (let i = 0; i < s.length; i++) {
        if(vars.hasOwnProperty(s[i])) {
            stack.push(variable(s[i]))
        } else if (s[i] in func) {
            stack.push(parseFunc(stack, func[s[i]]))
        } else {
            stack.push(cnst(s[i]))
        }
    }
    return stack.pop();
}
