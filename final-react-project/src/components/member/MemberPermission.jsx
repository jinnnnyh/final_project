function MemberPermission () {
  return (
    <section>
      <p className={'mb-1 text-black-50'}>CheckManager</p>
      <h1 className={'mb-4'}>승인대기</h1>
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
          <th scope={'col'}>번호</th>
          <th scope={'col'}>아이디</th>
          <th scope={'col'}>이름</th>
          <th scope={'col'}>전화번호</th>
          <th scope={'col'}>소속기관</th>
          <th scope={'col'}>직위</th>
          <th scope={'col'}>승인</th>
        </tr>
        </thead>
        <tbody>
        <tr>
          <td>1</td>
          <td>iu</td>
          <td>아이유</td>
          <td>010-1111-1111</td>
          <td>부산시청교육협회</td>
          <td>총무</td>
          <td>
            <button type={'submit'} className={'btn btn-outline-danger'}>승인대기</button>
          </td>
        </tr>
        <tr>
          <td>2</td>
          <td>iu</td>
          <td>아이유</td>
          <td>010-1111-1111</td>
          <td>부산시청교육협회</td>
          <td>총무</td>
          <td>
            <button type={'submit'} className={'btn btn-point'}>승인완료</button>
          </td>
        </tr>
        <tr>
          <td>3</td>
          <td>iu</td>
          <td>아이유</td>
          <td>010-1111-1111</td>
          <td>부산시청교육협회</td>
          <td>총무</td>
          <td>
            <button type={'submit'} className={'btn btn-point'}>승인완료</button>
          </td>
        </tr>
        </tbody>
      </table>
    </section>
  )
}

export default MemberPermission;