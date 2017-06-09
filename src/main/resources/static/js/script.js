// "use strict";
var possible_steps = [['a1','b2','c3','d4','e5','f6','g7','h8'],
                      ['a3','b4','c5','d6','e7','f8'],
                      ['c1','d2','e3','f4','g5','h6'],
                      ['a5','b6','c7','d8'],
                      ['e1','f2','g3','h4'],
                      ['h2','g3','f4','e5','d6','c7','b8'],
                      ['g1','f2','e3','d4','c5','b6','a7'],
                      ['h4','g5','f6','e7','d8'],
                      ['e1','d2','c3','b4','a5'],
                      ['h6','g7','f8'],
                      ['c1','b2','a3']],
    fields = {},
    current_active_field = "",
    current_step = "white";
function stepTurn(id) {
  // if (fields[id]._color == current_step) {
    // эту часть кода прост добавь в
    for (var prop in fields) {
      fields[prop].active = false;
      fields[prop].pseudo_active = false;
    }
    fields[id].active = true;
    fields[id].checkSteps();
  // }
}
class Field {
  constructor(dom, id, color, enemy_color, type, canEat, active, pseudo_active, forward, back, top_limit, right_limit, bottom_limit, left_limit) {
    this.dom = dom;
    this.id = id;
    this.color = color;
    this.enemy_color = enemy_color;
    this.type = type;
    this.canEat = canEat;
    this.active = active;
    this.pseudo_active = pseudo_active;
    this.forward = forward;
    this.back = back;
    this.top_limit = top_limit;
    this.right_limit = right_limit;
    this.bottom_limit = bottom_limit;
    this.left_limit = left_limit;
  }
  get dom() {
    return _dom;
  }
  set dom(dom) {
    this._dom = dom;
  }
  get id() {
    return _id;
  }
  set id(id) {
    this._id = id;
  }
  get color() {
    return _color;
  }
  set color(color) {
    this._color = color;
    this.active = false;
    this.pseudo_active = false;
    if (color == "none") {
      this._type = "none";
      this._forward = false;
      this._back = false;
      this._enemy_color = "none";
      this._dom.classList.remove("black");
      this._dom.classList.remove("white");
    } else if (color == "black") {
      this._dom.classList.add("black");
      this._enemy_color = "white";
      this._dom.classList.remove("white");
    } else if (color == "white") {
      this._dom.classList.add("white");
      this._enemy_color = "black";
      this._dom.classList.remove("black");
    }
  }
  get enemy_color() {
    return _enemy_color;
  }
  set enemy_color(color) {
    this._enemy_color = color;
  }
  get type() {
    return _type;
  }
  set type(type) {
    this._type = type;
  }
  get canEat() {
    return _canEat;
  }
  set canEat(canEat) {
    this._canEat = canEat;
  }
  get active() {
    return _active;
  }
  set active(active) {
    this._active = active;
    if (active) {
      current_active_field = this;
      this._dom.classList.add("active");
    } else {
      this._dom.classList.remove("active");
    }
  }
  get pseudo_active() {
    return _pseudo_active;
  }
  set pseudo_active(pseudo_active) {
    var actField,
        prop,
        currentField = this,
        destroyed_field,
        index1,
        index2;
    currentField._pseudo_active = pseudo_active;
    if (pseudo_active == "reshreshAll") {
      for (prop in fields) {
        fields[prop].pseudo_active = false;
      }
    } else if (pseudo_active) {
      currentField._dom.onclick = function() {
        for (prop in fields) {
          if (fields[prop]._active == true) {
            actField = fields[prop];
            break;
          }
        }
        possible_steps.forEach(function(diagonal) {
          index1 = diagonal.indexOf(current_active_field._id);
          index2 = diagonal.indexOf(currentField._id);
          if (index1 > -1 && index2 > -1 && Math.abs(index1 - index2) > 1) {
            if (index1 > index2) {
              destroyed_field = fields[diagonal[index1-1]];
            } else {
              destroyed_field = fields[diagonal[index1+1]];
            }
          }
        });
        if (destroyed_field !== undefined) {
          // console.log("destroyed: " + destroyed_field._id);
          destroyed_field.color = "none";
        }
        currentField.pseudo_active = "reshreshAll";
        currentField.color = actField._color;
        currentField.type = actField._type;
        currentField.forward = actField._forward;
        currentField.back = actField._back;
        actField.color = "none";
        current_step = actField._enemy_color;
      }
      this._dom.classList.add("pseudo-active");
    } else {
      this._dom.onclick = function() {
      }
      this._dom.classList.remove("pseudo-active");
    }
  }
  get forward() {
    return _forward;
  }
  set forward(forward) {
    this._forward = forward;
  }
  get back() {
    return _back;
  }
  set back(back) {
    this._back = back;
  }
  get top_limit() {
    return this._top_limit;
  }
  set top_limit(top_limit) {
    this._top_limit = top_limit;
  }
  get bottom_limit() {
    return this._bottom_limit;
  }
  set bottom_limit(bottom_limit) {
    this._bottom_limit = bottom_limit;
  }
  get right_limit() {
    return this._right_limit;
  }
  set right_limit(right_limit) {
    this._right_limit = right_limit;
  }
  get left_limit() {
    return this._left_limit;
  }
  set left_limit(left_limit) {
    this._left_limit = left_limit;
  }
  // stepTo() {
  //   console.log(this);
  //   alert('f');
  // }
  checkSteps(stepOvers) {
    var i,
        field = this,
        index,
        open_steps = [],
        enemy_list_top = [],
        enemy_list_bottom = [];
    if (stepOvers === undefined) {
      var stepOvers = [];
    }
    // console.log(current_active_field);
    // console.log(current_last_step);
    possible_steps.forEach(function(diagonal, i, arr) {
      index = diagonal.indexOf(field._id);
      if (index > -1) {
        if (fields[diagonal[index-2]] !== undefined) {
          if (fields[diagonal[index-1]]._color == field._enemy_color &&
          fields[diagonal[index-2]]._color == "none") {
            open_steps.push(fields[diagonal[index-2]]);
            enemy_list_bottom.push(fields[diagonal[index-1]]);
          }
        }
        if (fields[diagonal[index+2]] !== undefined) {
          if (fields[diagonal[index+1]]._color == field._enemy_color &&
          fields[diagonal[index+2]]._color == "none") {
            open_steps.push(fields[diagonal[index+2]]);
            enemy_list_top.push(fields[diagonal[index+1]]);
          }
        }
        if (fields[diagonal[index-1]] !== undefined) {
          if (fields[diagonal[index-1]]._color == "none" && field._back) {
            open_steps.push(fields[diagonal[index-1]]);
          }
        }
        if (fields[diagonal[index+1]] !== undefined) {
          if (fields[diagonal[index+1]]._color == "none" && field._forward) {
            open_steps.push(fields[diagonal[index+1]]);
          }
        }
      }
    });
    open_steps.forEach(function(item, key, arr) {
      item.pseudo_active = true;
      // console.log(item);
    });
  }
}

$(document).ready(function() {
  var game_field = document.getElementById("game-main-field");
  if (game_field !== null) {
  var start_game = $(".start-game"),
      end_game = $(".end-game"),
      waiting_time = $("#waiting-time"),
      els = game_field.getElementsByTagName("td"),
      white_field = ['a1', 'a3', 'b2', 'c1', 'c3', 'd2', 'e1', 'e3', 'f2', 'g1', 'g3', 'h2'],
      black_field = ['a7', 'b6', 'b8', 'c7', 'd6', 'd8', 'e7', 'f6', 'f8', 'g7', 'h6', 'h8'];

      console.log(possible_steps);
      // alert (fields.join ("\n"));

  [].forEach.call(els, function(val) {
    var id = val.getAttribute("id"),
        color = "",
        enemy_color = "",
        back = false,
        forward = false,
        top_limit = false,
        right_limit = false,
        bottom_limit = false,
        left_limit = false;

    if (white_field.indexOf(id) >= 0) {
      color = "white";
      enemy_color = "black";
      forward = true;
      back = false;
    } else if (black_field.indexOf(id) >= 0) {
      color = "black";
      enemy_color = "white";
      forward = false;
      back = true;
    } else {
      color = "none";
      enemy_color = "none";
      forward = false;
      back = false;
    }

    val.classList.add(color);

    if (id.charAt(0) == "a") {
      left_limit = true;
    }
    if (id.charAt(0) == "h") {
      right_limit = true;
    }
    if (id.charAt(1) == "1") {
      bottom_limit = true;
    }
    if (id.charAt(1) == "8") {
      top_limit = true;
    }

    fields[id] = new Field(val, id, color, enemy_color, "saber", false, false, false, forward, back, top_limit, right_limit, bottom_limit, left_limit);

    val.addEventListener("click", function(e) {
      var id = val.getAttribute("id");
      if (fields[id]._color == "black" || fields[id]._color == "white") {
        stepTurn(id);
      }
    });
    console.log(fields[id]);
  });
}
  $("#start-game").on("click", function(e) {
    e.preventDefault();
    waiting_time.fadeIn();

  });
  $("#test").click(function() {
    // $.post("ajaxtest", JSON.stringify({status: 0, currentPlayer: 1}), function(data) {
    //     alert("Data: " + data + "\nStatus: " + status);
    //     console.log("Data: " + data + "\nStatus: " + status);
    //     console.log(typeof(data));
    //     console.log(data[0])
    // });
    $.ajax({
      type: "POST",
      url: "ajaxtest",
      data: JSON.stringify({status: 3, currentPlayer: 8}),
      contentType: 'application/json',
      success: function(data) {
        console.log(data);
        if (data.status == 'OK') alert('Person has been added');
        else alert('Failed adding person: ' + data.status + ', ' + data.errorMessage);
      }
    });
  });
  $("#form").on("submit", function(e) {
    e.preventDefault();
    console.log('inaction');
    var urla = $(this).attr("action"),
        form = formToObj(this),
        te = {login: $("#login").val(), password: $("#password").val()};
    window.errors = [];
    if (checkEmpty(form) && checkTags(form) && checkPass(form)) {
    console.log('inajax');
      $.ajax({
        type: "POST",
        url: urla,
        data: JSON.stringify(form),
        contentType: 'application/json',
        success: function(data) {
          console.log(data);
          if (data.status == 'OK') {
            showError(data.errorMessage);
            location.href = data.errorMessage;
          } else {
             showError(data.errorMessage);
          }
        }
      });
    } else {
      showError(window.errors);
    }
  });
});
function showError(err) {
  var errField = $("#errorMessage");
  errField.html(err).fadeIn(600).delay(2000).fadeOut(600);
}
function formToObj(form) {
  var $inputs = $(form).find('input').not('input[type="submit"]'),
      obj = {};
  $inputs.each(function() {
    if ($(this).attr('type') != 'radio' || $(this).prop('checked')) {
      obj[$(this).attr('name')] = $(this).val() == "" ? "" : $(this).val();
    }
  });
  return obj;
}
function checkTags(obj) {
  for (i in obj) {
    if (obj[i] !== obj[i].stripTags()) {
      window.errors.push("Удалите теги");
      return false;
    }
  }
  return true;
}
function checkEmpty(obj) {
  for (i in obj) {
    if (obj[i] !== obj[i].stripTags() || obj[i].trim() == "") {
      window.errors.push("Заполните поля");
      return false;
    }
  }
  return true;
}
function checkPass(obj) {
  if (obj.hasOwnProperty('password') && obj['password'].length < 4) {
    window.errors.push("Длина пароля должна быть больше 4 символов");
    return false;
  }
  if ( obj.hasOwnProperty('checkPassword') && (obj['checkPassword'] != obj['password']) ) {
    window.errors.push("Пароли не совпадают");
    return false;
  }
  return true;
}
String.prototype.stripTags = function() {
  return this.replace(/<\/?[^>]+>/g, '');
};


// function formChecker(form) {
//   this._form = form;
//   this.form = form;
//   this.inputs = $(form).find('input').not('input[type="submit"]');
//   this.password = $(form).find('input[type="password"]');
// }
// formChecker.prototype.sayHi = function() {
//   console.log(this._form);
//   console.log(this.form);
// }
// formChecker.prototype.print = function() {
//   console.log("Форма: \n" + this.form + "\n Инпуты: \n" + this.printObj(this.inputs)
//   + "\n Пароль: \n" + this.printObj(this.password) + "\n Проверка: \n" + this.printObj(this.passwod));
// }
// formChecker.prototype.printObj = function(obj) {
//   var result = "";
//   $(obj).each(function (i, el) {
//     result += "Ключ: " + i + "; Значение: " + el + "\n";
//   });
//   return result;
// }
// formChecker.prototype.checkTrim = function(obj) {
//   $(obj).each(function (i, el) {
//     if ()
//   });
//   return result;
// }
