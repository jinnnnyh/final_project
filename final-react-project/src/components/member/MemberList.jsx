import Member from "../../pages/Member.jsx";

function MemberList () {

  return (
    <section>
      <Member/>
      <div>
        <table className={'table table-custom'}>
          <colgroup>
            <col width={"7%"}/>
            <col width={"7%"}/>
            <col width={"auto"}/>
            <col width={"15%"}/>
            <col width={"20%"}/>
            <col width={"15%"}/>
            <col width={"15%"}/>
          </colgroup>
          <thead>
          <tr>
            <th scope={'col'}></th>
            <th scope={'col'}>번호</th>
            <th scope={'col'}>아이디</th>
            <th scope={'col'}>이름</th>
            <th scope={'col'}>전화번호</th>
            <th scope={'col'}>소속기관</th>
            <th scope={'col'}>직위</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>1</td>
            <td>iu</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>부산시청교육협회</td>
            <td>총무</td>
          </tr>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>2</td>
            <td>iu</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>부산시청교육협회</td>
            <td>총무</td>
          </tr>
          <tr>
            <td>
              <div className="form-check">
                <input className="form-check-input" type="checkbox" value="" id="checkbox"/>
                <label className="form-check-label" htmlFor="checkbox"></label>
              </div>
            </td>
            <td>3</td>
            <td>iu</td>
            <td>아이유</td>
            <td>010-1111-1111</td>
            <td>부산시청교육협회</td>
            <td>총무</td>
          </tr>
          </tbody>
        </table>


        <div className={'d-flex justify-content-between mt-5'}>
          <div className={'justify-content-start'}>
          <button type={'submit'} className={'btn btn-outline-danger'}>삭제</button>
          </div>
          <div className={'justify-content-end'}>
            <button type={'submit'} className={'btn btn-point'}>수정</button>
          </div>
        </div>
      </div>

    </section>
  )
}

export default MemberList;