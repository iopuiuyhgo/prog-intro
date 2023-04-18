const sum = (...args) => [0, ...args].reduce((a, b) => a + b)
const vars = {
    "x":0,
    "y":1,
    "z":2,
}
function AbstractExp(f, fString, fPrefix, ...args) {
    this.evaluate = (...argv) => f(...args?.map(x => x.evaluate(...argv)))
    this.toString = () => fString(...args?.map(x => x.toString()))
    this.prefix = () => fPrefix(...args?.map(x => x.prefix()))
}
function Const(a) { this.a = a }
Const.prototype.evaluate = function () { return Number(this.a) }
Const.prototype.toString = function () { return this.a.toString() }
Const.prototype.prefix = function () { return this.a.toString() }
function Variable(a) {
    this.a = a
}
Variable.prototype.evaluate = function (...args) { return Number(args[vars[this.a]]) }
Variable.prototype.toString = function () { return this.a.toString() }
Variable.prototype.prefix = function () { return this.a.toString() }
function Add(a, b) { AbstractExp.call(this, (a, b) => a+b, (a, b) => a + " " + b + " +", (a, b) => "(+ " + a + " " + b + ")", a, b) }
function Subtract(a, b) { AbstractExp.call(this, (a, b) => a - b, (a, b) => a + " " + b + " -", (a, b) => "(- " + a + " " + b + ")", a, b) }
function Multiply(a, b) { AbstractExp.call(this, (a, b) => a * b, (a, b) => a + " " + b + " *", (a, b) => "(* " + a + " " + b + ")", a, b) }
function Divide(a, b) { AbstractExp.call(this, (a, b) => a / b, (a, b) => a + " " + b + " /", (a, b) => "(/ " + a + " " + b + ")", a, b) }
function ArcTan(a) {
    return new AbstractExp(a => Math.atan(a), a => a + " atan", a => "(atan " + a + ")", a)
}
function ArcTan2(a, b) {
    return new AbstractExp((a, b) => Math.atan2(a, b), (a, b) => a + " " + b + " atan2", a => "(atan2 " + a + ")", a, b)

}
function Negate(a) { return new AbstractExp(a => -a, a => a + " negate", a => "(negate " + a + ")", a) }

function Exp(a) { return new AbstractExp(a => Math.exp(a), a => a + " exp", a => "(exp " + a + ")", a) }
function Ln(a) {
    return new AbstractExp(a => Math.log(a), a => a + " ln", a => "(ln " + a + ")", a)
}
function Sum(...args) {
    return new AbstractExp((...args) => sum(...args), (...args) => ([...args] + " sum").replaceAll(","," "), (...args) => ("(sum " + [...args] + ")").replaceAll(",", " "), ...args)
}
function Avg(...args) {
    return new AbstractExp((...args) => sum(...args)/args.length, (...args) => ([...args] + " avg").replaceAll(","," "), (...args) => ("(avg " + [...args] + ")").replaceAll(",", " "), ...args)
}
const funcPref = {
    "+": [(...args) => new Add(...args), 2],
    "-": [(...args) => new Subtract(...args), 2],
    "*": [(...args) => new Multiply(...args), 2],
    "/": [(...args) => new Divide(...args), 2],
    "negate": [(...args) => new Negate(...args), 1],
    "sum": [(...args) => new Sum(...args), -1],
    "avg": [(...args) => new Avg(...args), -1],
    "exp": [(...args) => new Exp(...args), 1],
    "ln": [(...args) => new Ln(...args), 1],
    "atan": [(...args) => new ArcTan(...args), 1],
    "atan2": [(...args) => new ArcTan2(...args), 2],
}
function parseFunc(stack, s) {
    if (funcPref[s][1] === 0) { return funcPref[s][0](); }
    const tmp = stack.splice(stack.length - funcPref[s][1], stack.length)
    return funcPref[s][0](...tmp)
}
function parse(s) {
    s = s.split(" ").filter(ch => ch.length > 0);
    let stack = [];
    for (let i = 0; i < s.length; i++) {
        if(vars.hasOwnProperty(s[i])) {
            stack.push(new Variable(s[i]))
        } else if (!isNaN(Number(s[i]))) {
            stack.push(new Const(s[i]))
        } else {
            stack.push(parseFunc(stack, s[i]))
        }
    }
    return stack.pop();
}

function ParseError(message) {
    Error.call(this, message)
    this.message = message
}
ParseError.prototype = Object.create(Error.prototype)
ParseError.prototype.name = "ParseError"
ParseError.prototype.constructor = ParseError;

function parsePrefix(s) {
    let brackets = {}
    let exp
    let newS = ""
    for (let i = 0; i < s.length; i++) {
        if (s[i] === "(" || s[i] === ")") {
            newS += " " + s[i] + " "
        } else {
            newS += s[i]
        }
    }
    exp = newS.split(" ").filter(ch => ch.length > 0)
    let tmp = []
    for (let i = 0; i < exp.length; i++) {
        if (exp[i] === "(") {
            tmp.push(i)
        }
        if (exp[i] === ")") {
            brackets[tmp.pop()] = i
        }
    }
    if (tmp.length !== 0) {
        throw new ParseError("invalid bracket sequence")
    }

    return parsing(0, exp.length - 1)


    function parsing(l, r) {
        if (exp[l] === "(") {
            return parsing(l + 1, r - 1)
        } else if (exp[l] in funcPref) {
            const func = funcPref[exp[l]]
            let args = []
            l += 1
            while (l <= r) {
                if (exp[l] === "(") {
                    args.push(parsing(l + 1, brackets[l] - 1))
                    l = brackets[l] + 1
                } else if (exp[l] in vars) {
                    args.push(new Variable(exp[l]))
                    l += 1
                } else if (!isNaN(Number(exp[l]))) {
                    args.push(new Const(Number(exp[l])))
                    l += 1
                } else {
                    throw new ParseError("invalid operand " + exp[l])
                }
            }
            if (func[1] !== -1 && func[1] !== args.length) {
                throw new ParseError("invalid number of operands")
            }
            return func[0](...args)
        } else if (l !== r) {
            throw new ParseError("missed operator")
        } else if (exp[l] in vars) {
            return new Variable(exp[l])
        } else if (!isNaN(Number(exp[l]))) {
            return new Const(Number(exp[l]))
        } else {
            throw new ParseError("invalid token " + exp(l))
        }
    }
}